package com.solvd.notix.web.dto.getdto;

import com.solvd.notix.web.dto.Notebook;
import com.solvd.notix.web.pages.NotebookDescriptionPage;

import static com.solvd.notix.web.pages.NotebookDescriptionPage.DescriptionCommonField;

public class GetNotebook {

    public static Notebook getNotebookByPage(NotebookDescriptionPage page) {
        return Notebook.builder().
                brandName(page.getBrandName()).
                year(page.getDescriptionItem(DescriptionCommonField.YEAR)).
                cpu(page.getDescriptionItem(DescriptionCommonField.CPU_MODEL)).
                ram(page.getDescriptionItem(DescriptionCommonField.RAM)).
                resolution(page.getDescriptionItem(DescriptionCommonField.RESOLUTION)).
                build();
    }
}
