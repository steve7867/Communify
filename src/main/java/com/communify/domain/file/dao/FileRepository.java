package com.communify.domain.file.dao;

import com.communify.domain.file.dto.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileRepository {

    void insertFileInfoList(List<FileInfo> fileInfoList);

    Optional<FileInfo> findByStoredFilename(String storedFilename);

    List<FileInfo> findAllByPostId(Long postId);

    void deleteAll(Long postId);
}
