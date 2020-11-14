package org.picon.config;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

public interface ApiDocumentUtils {
    static OperationRequestPreprocessor getDocumentRequest() {
        return  preprocessRequest(modifyUris()
                        .scheme("http")
                        .host("www.yappandone17.shop")
                        .removePort(),
                prettyPrint()); // 문서의 request를 예쁘게 출력
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint()); // 문서의 response를 예쁘게 출력
    }

}