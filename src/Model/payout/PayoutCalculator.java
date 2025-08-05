package Model.payout;

import Model.grid.Cluster;

import java.sql.SQLException;
import java.util.List;

public interface PayoutCalculator {
    float calculatePayoutForMatch(List<Cluster> clusters, float betAmount);

}
