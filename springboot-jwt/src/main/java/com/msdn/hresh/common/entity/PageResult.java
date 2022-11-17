package com.msdn.hresh.common.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2021/5/3 10:00
 * @description 解析分页数据，Page和PageInfo是针对Mybatis查询到的分页数据进行处理；
 * <p> org.springframework.data.domain.Page是针对Jpa查询的分页数据进行处理
 * Ipage是针对Mybatis Plus查询的分页数据进行处理，实际上我们解析MbpPage
 */
@Getter
@Setter
public class PageResult<T> {

  /**
   * 总条数
   */
  private Long total;
  /**
   * 总页数
   */
  private Integer pageCount;
  /**
   * 每页数量
   */
  private Integer pageSize;
  /**
   * 当前页码
   */
  private Integer pageNum;

  /**
   * 分页数据
   */
  private List<T> data;

  /**
   * 处理Mybatis Plus分页结果
   */
  public static <T> PageResult<T> ok(IPage<T> iPage) {
    PageResult<T> result = new PageResult<>();
    result.setTotal(iPage.getTotal());
    int pageCount = (int) Math.ceil((double) result.getTotal() / iPage.getSize());
    result.setPageCount(pageCount);
    result.setPageNum((int) iPage.getCurrent());
    result.setPageSize((int) iPage.getSize());
    result.setData(iPage.getRecords());
    return result;
  }
}
