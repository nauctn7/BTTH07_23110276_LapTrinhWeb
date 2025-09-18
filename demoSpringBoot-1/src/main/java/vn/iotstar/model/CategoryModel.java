package vn.iotstar.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryModel {
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    private String name;

    // cờ để Controller biết đang edit hay add (giống hình)
    private Boolean isEdit = false;
}
