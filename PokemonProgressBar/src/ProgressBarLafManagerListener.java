import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import javax.swing.UIManager;
import org.jetbrains.annotations.NotNull;

public class ProgressBarLafManagerListener implements LafManagerListener {
    public ProgressBarLafManagerListener() {
        updateProgressBarUI();
    }

    public void lookAndFeelChanged(@NotNull LafManager lafManager) {
        if (lafManager == null)
            $$$reportNull$$$0(0);
        updateProgressBarUI();
    }

    private static void updateProgressBarUI() {
        UIManager.put("ProgressBarUI", ProgressBarUi.class.getName());
        UIManager.getDefaults().put(ProgressBarUi.class.getName(), ProgressBarUi.class);
    }
}
