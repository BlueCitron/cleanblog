package bluecitron.cleanblog.web.dto;

import bluecitron.cleanblog.web.dto.command.BaseCommand;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class CommonListResponse <E, D> {
    List<D> list;
    BaseCommand searchInfo;
    Page<E> pageInfo;

    public CommonListResponse(List<D> list, BaseCommand searchInfo, Page<E> pageInfo) {
        this.list = list;
        this.searchInfo = searchInfo;
        this.pageInfo = pageInfo;
    }
}
