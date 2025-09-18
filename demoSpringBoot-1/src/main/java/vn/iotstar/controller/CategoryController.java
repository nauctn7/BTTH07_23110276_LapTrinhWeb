package vn.iotstar.controller;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.iotstar.entity.Category;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.service.ICategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private ICategoryService service;
    @GetMapping({"", "/"})       // hỗ trợ cả /admin/categories và /admin/categories/
    public String list(ModelMap model) {
        model.addAttribute("categories", service.findAll());
        return "admin/categories/list";
    }


    // --- ADD form ---
    @GetMapping("add")
    public String add(ModelMap model) {
        CategoryModel cateModel = new CategoryModel();
        cateModel.setIsEdit(false);
        model.addAttribute("category", cateModel);
        return "admin/categories/addOrEdit";
    }

    // --- EDIT form ---
    @GetMapping("edit/{categoryId}")
    public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
        Category entity = service.findById(categoryId).orElse(null);
        if (entity == null) {
            model.addAttribute("message", "Category is not exist!!!!");
            return new ModelAndView("forward:/admin/categories/searchpaginated", model);
        }
        CategoryModel catModel = new CategoryModel();
        BeanUtils.copyProperties(entity, catModel);
        catModel.setIsEdit(true);
        model.addAttribute("category", catModel);
        return new ModelAndView("admin/categories/addOrEdit", model);
    }

    // --- SAVE OR UPDATE (đúng tên theo slide) ---
    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("category") CategoryModel catModel,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/categories/addOrEdit");
        }
        // copy từ DTO sang Entity
        Category entity = new Category();
        BeanUtils.copyProperties(catModel, entity);

        service.save(entity);

        String message = (Boolean.TRUE.equals(catModel.getIsEdit()))
                ? "Category is Edited!!!!!!!"
                : "Category is saved!!!!!!!";

        model.addAttribute("message", message);
        // forward đến trang search + paging như slide
        return new ModelAndView("forward:/admin/categories/searchpaginated", model);
    }

    // --- DELETE ---
    @GetMapping("delete/{categoryId}")
    public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
        service.deleteById(categoryId);
        model.addAttribute("message", "Category is deleted!!!!!!!");
        return new ModelAndView("forward:/admin/categories/searchpaginated", model);
    }

    @RequestMapping(value = "searchpaginated", method = {RequestMethod.GET, RequestMethod.POST})
    public String search(@RequestParam(required = false) String name,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "3") int size,
                         ModelMap model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Category> resultPage = service.findByNameContaining(name, pageable);
        model.addAttribute("categoryPage", resultPage);
        model.addAttribute("name", name);
        return "admin/categories/searchpaging";
    

    }
}
