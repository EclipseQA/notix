package com.solvd.notix.web.dto.getdto;

import com.solvd.notix.web.dto.NotebookModel;
import com.solvd.notix.web.pages.NotebookDescriptionPage;

import static com.solvd.notix.web.pages.NotebookDescriptionPage.DescriptionCommonField;

public class GetNotebookModel {

    public static NotebookModel getNotebookModel(NotebookDescriptionPage page) {
        return NotebookModel.builder().
                productLine(page.getDescriptionItem(DescriptionCommonField.PRODUCT_LINE)).
                year(page.getDescriptionItem(DescriptionCommonField.YEAR)).
                cpu(page.getDescriptionItem(DescriptionCommonField.CPU_MODEL)).
                ram(page.getDescriptionItem(DescriptionCommonField.RAM)).
                resolution(page.getDescriptionItem(DescriptionCommonField.RESOLUTION)).
                build();
    }
}
