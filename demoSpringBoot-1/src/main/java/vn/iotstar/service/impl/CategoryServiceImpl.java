package vn.iotstar.service.impl;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public <S extends Category> S save(S entity) {
        return repo.save(entity);
    }

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Page<Category> findByNameContaining(String name, Pageable pageable) {
        if (StringUtils.isBlank(name)) {
            return repo.findAll(pageable);
        }
        return repo.findByNameContaining(name, pageable);
    }
}
