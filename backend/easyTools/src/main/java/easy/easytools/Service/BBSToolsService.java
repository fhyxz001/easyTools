package easy.easytools.Service;

import easy.easytools.Entity.NewsFolder.News;

public interface BBSToolsService {
    News getNewsByUrl(String url);
}
