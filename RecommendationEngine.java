import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class RecommendationEngine {

    public static void main(String[] args) {
        try {
            // Load data
            DataModel model = new FileDataModel(new File("data.csv"));

            // Calculate similarity between users
            UserSimilarity similarity =
                    new PearsonCorrelationSimilarity(model);

            // Find nearest users
            UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(2, similarity, model);

            // Create recommender
            Recommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Get recommendations for user ID = 1
            List<RecommendedItem> recommendations =
                    recommender.recommend(1, 2);

            System.out.println("Recommended items for User 1:");
            for (RecommendedItem item : recommendations) {
                System.out.println(
                        "Item ID: " + item.getItemID() +
                        " | Estimated Preference: " + item.getValue()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
