package com.example.community.admin.forbiddenWord.service;

import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import com.example.community.admin.forbiddenWord.repo.ForbiddenWordRepository;
import com.example.community.admin.forbiddenWord.util.BooleanTextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    @Transactional(readOnly = true)
    public byte[] downloadCsv() throws IOException {
        byte[] csvFile;
        String[] columns = {"idx","text","isPostForbidden","isCommentForbidden","isGuestBookForbidden"};

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
}
