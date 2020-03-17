package bluecitron.cleanblog.core.domain.exception;

import bluecitron.cleanblog.common.CommonException;

public class CategoryDepthOverException extends CommonException {

    public CategoryDepthOverException() {
        super(String.format("카테고리 Depth는 1이상 커질 수 없습니다."));
    }
}
