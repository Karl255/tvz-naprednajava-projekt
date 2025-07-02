package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.model.User;

import java.util.List;

public interface EntityService<TEntity, TCreateRequest> {
    List<TEntity> findAll();

    TEntity create(TCreateRequest commentRequest, User user);

    TEntity update(Long id, TCreateRequest commentRequest);

    void deleteById(Long id);
}
