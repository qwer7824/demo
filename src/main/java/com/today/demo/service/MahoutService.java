package com.today.demo.service;

import com.today.demo.config.DataSourceConfig;
import com.today.demo.dto.BoardResponseDTO;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MahoutService {
    private final DataSourceConfig dataSourceConfig;
    private final BoardService boardService;

    public List<BoardResponseDTO> BoardRecommand(long boardId) {
        DataSource dataSource = dataSourceConfig.getDataSource();
        List<BoardResponseDTO> boardRecommendations = new ArrayList<>();

        try {
            DataModel dataModel = new MySQLJDBCDataModel(
                    dataSource, "board", "marker_id",
                    "board_id", "star", null);

            ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
            ItemBasedRecommender recommender = new GenericBooleanPrefItemBasedRecommender(dataModel, similarity);
            List<RecommendedItem> recommendations = recommender.mostSimilarItems(boardId, 2);

            for (RecommendedItem recommendation : recommendations) {
                long recommendedBoardId = recommendation.getItemID();
                List<BoardResponseDTO> recommendedBoards = boardService.getBoardRecommand(recommendedBoardId);
                boardRecommendations.addAll(recommendedBoards);
            }
        } catch (TasteException e) {
            e.printStackTrace();
        } finally {
            // 리소스 정리
            if (dataSource != null) {
                if (dataSource instanceof HikariDataSource) {
                    ((HikariDataSource) dataSource).close();
                }
            }
        }

        return boardRecommendations;
    }
}
