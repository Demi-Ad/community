package com.example.community.admin.forbiddenWord.service;

import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import com.example.community.admin.forbiddenWord.repo.ForbiddenWordRepository;
import com.example.community.admin.forbiddenWord.util.BooleanTextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ForbiddenWordCsvService {

    private final ForbiddenWordRepository repository;
    private final BooleanTextUtil booleanTextUtil;

    private final String[] columns = {"idx", "text", "isPostForbidden", "isCommentForbidden", "isGuestBookForbidden"};

    @Transactional(readOnly = true)
    public byte[] downloadCsv() throws IOException {
        byte[] csvFile;


        try (StringWriter sw = new StringWriter(); CSVPrinter csvPrinter = new CSVPrinter(sw, CSVFormat.DEFAULT.withHeader(columns))) {
            for (ForbiddenWord forbiddenWord : repository.findAll()) {
                List<String> csvData = new ArrayList<>(List.of(forbiddenWord.getId().toString(), forbiddenWord.getForbiddenText()));
                csvData.addAll(booleanTextUtil.convert(forbiddenWord.getIsPostForbidden(),
                        forbiddenWord.getIsCommentForbidden(),
                        forbiddenWord.getIsGuestBookForbidden()));
                csvPrinter.printRecord(csvData);
            }
            sw.flush();
            csvFile = sw.toString().getBytes(StandardCharsets.UTF_8);
            return csvFile;
        } catch (IOException e) {
            log.error("csv download err!", e);
            throw e;
        }
    }

    public void csvToDatabase(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        CSVParser parse = CSVFormat.DEFAULT.withHeader(columns).parse(new InputStreamReader(inputStream));

        parse.getRecords().stream()
                .filter(strings -> strings.getRecordNumber() != 1)
                .map(strings -> wrap(() -> csvToEntityMapping(strings)))
                .forEach(this::updateAndSave);
    }

    private <T> T wrap(Supplier<T> s) {
        try {
            return s.get();
        } catch (Exception e) {
            log.error("CSV Parsing Error ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private ForbiddenWord csvToEntityMapping(CSVRecord strings) throws RuntimeException {
        String idx = strings.get(columns[0]);
        String text = strings.get(columns[1]);
        String isPostForbidden = strings.get(columns[2]);
        String isCommentForbidden = strings.get(columns[3]);
        String isGuestBookForbidden = strings.get(columns[4]);

        return ForbiddenWord.builder()
                .id(Long.parseLong(idx))
                .forbiddenText(text)
                .isPostForbidden(isPostForbidden.equals("Y"))
                .isCommentForbidden(isCommentForbidden.equals("Y"))
                .isGuestBookForbidden(isGuestBookForbidden.equals("Y"))
                .build();
    }

    private void updateAndSave(ForbiddenWord f) {
        repository.findById(f.getId())
                .ifPresentOrElse(entity -> entity.change(f), () -> repository.save(f));
    }
}
