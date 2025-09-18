package vn.iotstar.service;

import vn.iotstar.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    <S extends Category> S save(S entity);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    void deleteById(Long id);

    Page<Category> findAll(Pageable pageable);
    Page<Category> findByNameContaining(String name, Pageable pageable);
}
