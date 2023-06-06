package com.example.TODO.repository;

import com.example.TODO.entity.ToDo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

    ///можно так
    ///List<ToDo> findByDoneTrue();
    ///и так - второй вариант быстрее и лучше работает с большим объемом данных
    @Transactional
    @Modifying
    @Query("delete from ToDo t where t.done = :status")
    void deleteByDoneTrue(@Param("status") Boolean status);

    @Transactional
    @Modifying
    @Query("update ToDo t set t.done = :status")
    void changeStatus(@Param("status") Boolean status);

    @Transactional
    @Modifying
    @Query("update ToDo t set t.done = :status where t.id = :id")
    void changeStatusById(@Param("id") Integer id, @Param("status") Boolean status);


    @Transactional
    @Modifying
    @Query("update ToDo t set t.description = :text where t.id = :id")
    void changeTextById(@Param("id") Integer id, @Param("text") String text);
}
