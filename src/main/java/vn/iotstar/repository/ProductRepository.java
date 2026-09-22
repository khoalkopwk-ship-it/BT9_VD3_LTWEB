package vn.iotstar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.Product;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    @Query("""
        select p from Product p
        join fetch p.user u
        where lower(p.name) like lower(concat('%', :keyword, '%'))
        or lower(coalesce(p.description, ''))
           like lower(concat('%', :keyword, '%'))
    """)
    Page<Product> search(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    Page<Product> findByUserId(
            Long userId,
            Pageable pageable
    );

    long countByUserId(Long userId);
}