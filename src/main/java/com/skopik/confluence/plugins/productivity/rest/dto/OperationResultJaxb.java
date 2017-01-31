package com.skopik.confluence.plugins.productivity.rest.dto;

import com.skopik.confluence.plugins.productivity.page.OperationResult;
import com.skopik.confluence.plugins.productivity.page.PageData;
import com.skopik.confluence.plugins.productivity.page.PageOperationType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class OperationResultJaxb {

    @XmlElement
    private PageOperationType operationType;

    @XmlElement
    private boolean success;

    @XmlElements(@XmlElement(name = "pageData"))
    private List<PageDataJaxb> newPages = new ArrayList<>();

    public OperationResultJaxb(OperationResult operationResult) {
        this.operationType = operationResult.getOperationType();
        this.success = operationResult.isSuccess();

        for (PageData pageData : operationResult.getNewPages()) {
            this.newPages.add(new PageDataJaxb(pageData));
        }
    }

}
