package bluecitron.cleanblog.core.domain.exception;

import bluecitron.cleanblog.common.CommonException;

public class AccountDuplicatedException extends CommonException {

    String account;

    public AccountDuplicatedException(String account) {
        super(String.format("이미 존재하는 계정입니다. (account=%s)", account));
        this.account = account;
    }
}
