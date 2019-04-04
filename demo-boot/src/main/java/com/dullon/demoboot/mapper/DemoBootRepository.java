package com.dullon.demoboot.mapper;

import com.dullon.demoboot.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Repository 接口模板
 */

//@Mapper
public interface DemoBootRepository {

    List<Book> findByReader(@Param("reader") String reader);

    int save(Book book);
}
