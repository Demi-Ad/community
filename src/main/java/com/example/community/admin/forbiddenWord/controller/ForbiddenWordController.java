package com.example.community.admin.forbiddenWord.controller;

import com.example.community.admin.forbiddenWord.dto.ForbiddenWordDto;
import com.example.community.admin.forbiddenWord.dto.ForbiddenWordSearchDto;
import com.example.community.admin.forbiddenWord.service.ForbiddenWordCsvService;
import com.example.community.admin.forbiddenWord.service.ForbiddenWordService;
import com.example.community.admin.forbiddenWord.validator.DuplicateForbiddenWordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/forbiddenWord")
public class ForbiddenWordController {

    private final ForbiddenWordService forbiddenWordService;
    private final ForbiddenWordCsvService forbiddenWordCsvService;
    private final DuplicateForbiddenWordValidator validator;
    @GetMapping()
    public String forbiddenWordForm(Model model,
                                    @ModelAttribute("forbiddenWordSearch") ForbiddenWordSearchDto forbiddenWordSearchDto) {
        List<ForbiddenWordDto> pagination = forbiddenWordService.forbiddenWordList(forbiddenWordSearchDto.createSpecification());

        model.addAttribute("forbiddenWordList",pagination);
        model.addAttribute("forbiddenWordForm", new ForbiddenWordDto());
        model.addAttribute("forbiddenWordSearch",new ForbiddenWordSearchDto());
        return "admin/forbiddenWord/forbiddenWordForm";
    }

    @PostMapping("/add")
    public String forbiddenWordAdd(@ModelAttribute("forbiddenWordForm") ForbiddenWordDto forbiddenWordDto, Errors errors, RedirectAttributes redirectAttributes) {
        validator.validate(forbiddenWordDto,errors);

        if (errors.hasErrors()) {
            redirectAttributes.addAttribute("err", URLEncoder.encode("이미 존재하는 값", StandardCharsets.UTF_8));
            return "redirect:/admin/forbiddenWord";
        }

        forbiddenWordService.save(forbiddenWordDto);
        return "redirect:/admin/forbiddenWord";
    }

    @PostMapping("/csvUpload")
    public String csvUpload (MultipartFile csv) {
        try {
            forbiddenWordCsvService.csvToDatabase(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin/forbiddenWord";
    }

    @PostMapping("/delete")
    public String forbiddenWordDelete(@ModelAttribute("id") Long id) {
        forbiddenWordService.delete(id);

        return "redirect:/admin/forbiddenWord";
    }

    @GetMapping("/csvDownload")
    @ResponseBody
    public ResponseEntity<byte[]> downloadCsv() {
        byte[] csvFile;
        HttpHeaders header = new HttpHeaders();

        try {
            csvFile = forbiddenWordCsvService.downloadCsv();
            header.setContentType(MediaType.valueOf("plain/text"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=forbiddenWordList.csv");
            header.setContentLength(csvFile.length);
            return new ResponseEntity<>(csvFile, header, HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
