package bluecitron.cleanblog.core.domain.exception;

import bluecitron.cleanblog.common.CommonException;

public class CategoryNameDuplicatedException extends CommonException {

    public CategoryNameDuplicatedException(String name) {
        super(String.format("중복된 카테고리 이름입니다.(name=%s)", name));
    }
}
