package org.example.patchs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowReward;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.neow.NeowReward.NeowRewardType.TEN_PERCENT_HP_BONUS;
import static com.megacrit.cardcrawl.neow.NeowReward.NeowRewardType.TWENTY_PERCENT_HP_BONUS;
import static org.example.Character.SlugCat.Enums.AddMaxWorkLevel;
import static org.example.Character.SlugCat.Enums.AddWorkLevel;

public class MyNeowReward extends NeowReward {
    private static final UIStrings Strings = CardCrawlGame.languagePack.getUIString("Liver:Neow");
    public MyNeowReward() {
        super(false);
        try {
            java.lang.reflect.Field type = NeowReward.class.getDeclaredField("type");
            type.setAccessible(true);
            type.set(this, AddWorkLevel);

            java.lang.reflect.Field optionLabel = NeowReward.class.getDeclaredField("optionLabel");
            optionLabel.setAccessible(true);
            String optionLabelField = (String) optionLabel.get(this);
            String[] parts = optionLabelField.split("#g");
            optionLabel.set(this, parts[0] + Strings.TEXT[0]  );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MyNeowReward(int category) {
        super(category);
        try {
            java.lang.reflect.Field rewardField = NeowReward.class.getDeclaredField("type");
            rewardField.setAccessible(true);
            NeowRewardType rewardObj = (NeowRewardType)rewardField.get(this);

                if (rewardObj == TEN_PERCENT_HP_BONUS) {
                    rewardField.set(this, AddWorkLevel);
                    java.lang.reflect.Field optionLabel = NeowReward.class.getDeclaredField("optionLabel");
                    optionLabel.setAccessible(true);
                    String optionLabelField = (String) optionLabel.get(this);
                    String[] parts = optionLabelField.split("#g");
                    optionLabel.set(this, parts[0] + Strings.TEXT[0]   );
                }
                if (rewardObj == TWENTY_PERCENT_HP_BONUS) {
                    rewardField.set(this, AddMaxWorkLevel);
                    java.lang.reflect.Field optionLabel = NeowReward.class.getDeclaredField("optionLabel");
                    optionLabel.setAccessible(true);
                    String optionLabelField = (String) optionLabel.get(this);
                    String[] parts = optionLabelField.split("#g");
                    optionLabel.set(this, parts[0] + Strings.TEXT[1]   );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();
    }

}
