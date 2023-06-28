package easy.easytools.Service;

import easy.easytools.Entity.News;

public interface BBSToolsService {
    News getNewsByUrl(String url);
}
