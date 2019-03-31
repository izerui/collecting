package com.github.collecting.jpa;

import com.github.collecting.jpa.base.Conditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by serv on 14-5-29.
 */
@NoRepositoryBean
public interface PlatformJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    T findOne(ID id);

    T findOne(Conditions conditions);

    T findOne(Conditions conditions, Sort sort);

    List<T> findAll(Conditions conditions);

    List<T> findAll(Conditions conditions, Sort sort);

    Page<T> findAll(Conditions conditions, Pageable pageable);

    <R> List<R> groupAll(List<String> selectFields, List<String> groupFields, Class<R> rClass);

    <R> List<R> groupAll(Sort sort, List<String> selectFields, List<String> groupFields, Class<R> rClass);

    <R> List<R> groupAll(Conditions conditions, List<String> selectFields, List<String> groupFields, Class<R> rClass);

    <R> List<R> groupAll(Conditions conditions, Sort sort, List<String> selectFields, List<String> groupFields, Class<R> rClass);

    <R> Page<R> groupAll(Conditions conditions, Pageable pageable, List<String> selectFields, List<String> groupFields, Class<R> rClass);

    <R> List<R> distinctAll(Class<R> rClass);

    <R> List<R> distinctAll(Sort sort, Class<R> rClass);

    <R> List<R> distinctAll(Conditions conditions, Class<R> rClass);

    <R> List<R> distinctAll(Conditions conditions, Sort sort, Class<R> rClass);

    <R> Page<R> distinctAll(Conditions conditions, Pageable pageable, Class<R> rClass);

    long count(Conditions conditions);

    long count(Conditions conditions, String columnName);

    <R> R sum(String columnName, Class<R> resultClass);

    <R> R sum(Conditions conditions, String columnName, Class<R> resultClass);

    <R> R max(String columnName, Class<R> resultClass);

    <R> R max(Conditions conditions, String columnName, Class<R> resultClass);

    <R> R min(String columnName, Class<R> resultClass);

    <R> R min(Conditions conditions, String columnName, Class<R> resultClass);

    <R> R avg(String columnName, Class<R> resultClass);

    <R> R avg(Conditions conditions, String columnName, Class<R> resultClass);

    <R> R aggregate(Conditions conditions, String aggregate, Class<R> resultClass);

    <R> R aggregate(String aggregate, Class<R> resultClass);

    void deleteAll(Conditions conditions);

    Class<T> getEntityClass();

}
