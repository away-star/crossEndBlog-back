package com.star.serviceOpenAi;

import com.star.serviceOpenAi.utils.OpenAiUtils;
import com.theokanning.openai.completion.CompletionChoice;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServiceOpenAiApplicationTests {

    @Test
    void contextLoads() {
        List<CompletionChoice> questionAnswer = OpenAiUtils.getInterviewQuestion("hallo");

        for (CompletionChoice completionChoice : questionAnswer) {
            System.out.println(completionChoice.getText());
        }
    }
}