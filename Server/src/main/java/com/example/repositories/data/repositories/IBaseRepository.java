package com.example.repositories.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /*
     * save(S entity): Lưu hoặc cập nhật một đối tượng vào cơ sở dữ liệu.
     * saveAll(Iterable<S> entities): Lưu hoặc cập nhật một danh sách đối tượng vào cơ sở dữ liệu.
     * findById(ID id): Tìm một đối tượng bằng cách sử dụng khóa chính (ID).
     * findAll(): Trả về tất cả các đối tượng từ cơ sở dữ liệu.
     * findAllById(Iterable<ID> ids): Trả về tất cả các đối tượng có các khóa chính trong danh sách được cung cấp.
     * deleteById(ID id): Xóa một đối tượng dựa trên khóa chính.
     * delete(T entity): Xóa một đối tượng đã được cung cấp.
     * deleteAll(): Xóa tất cả các đối tượng từ cơ sở dữ liệu.
     */
}