package crimsonEyed.patches;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import crimsonEyed.cards.temp.EnduringFlame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RenderBgNarutoPatch {
    @SpirePatch(clz = AbstractCard.class, method = "renderCardBg")
    public static class NarutoFix {
        private static Method renderHelperMethod;

        @SpireInsertPatch(loc = 1570)
        public static SpireReturn<Void> Insert(AbstractCard __instance, SpriteBatch sb, float x, float y, Color ___renderColor) {
            if (__instance instanceof EnduringFlame) {
                AbstractCard.CardColor color = __instance.color;
                CustomCard card = (CustomCard) __instance;
                Texture texture = null;
                TextureAtlas.AtlasRegion region = null;
                if (card.textureBackgroundSmallImg != null && !card.textureBackgroundSmallImg.isEmpty()) {
                    texture = card.getBackgroundSmallTexture();
                } else {
                    switch (card.type) {
                        case ATTACK:
                            if (BaseMod.getAttackBgTexture(color) == null) {
                                BaseMod.saveAttackBgTexture(color, ImageMaster.loadImage(BaseMod.getAttackBg(color)));
                            }

                            texture = BaseMod.getAttackBgTexture(color);
                            break;
                        case SKILL:
                            if (BaseMod.getSkillBgTexture(color) == null) {
                                BaseMod.saveSkillBgTexture(color, ImageMaster.loadImage(BaseMod.getSkillBg(color)));
                            }

                            texture = BaseMod.getSkillBgTexture(color);
                            break;
                        case POWER:
                            if (BaseMod.getPowerBgTexture(color) == null) {
                                BaseMod.savePowerBgTexture(color, ImageMaster.loadImage(BaseMod.getPowerBg(color)));
                            }

                            texture = BaseMod.getPowerBgTexture(color);
                            break;
                        default:
                            region = ImageMaster.CARD_SKILL_BG_BLACK;
                    }
                }

                if (texture != null) {
                    region = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
                }

                if (region == null) {
                    BaseMod.logger.info(color.toString() + " texture is null wtf");
                    return SpireReturn.Continue();
                } else {
                    renderHelper(card, sb, ___renderColor, region, x, y);
                    return SpireReturn.Return();
                }
            } else {
                return SpireReturn.Continue();
            }
        }

        private static void renderHelper(AbstractCard card, SpriteBatch sb, Color color, TextureAtlas.AtlasRegion region, float xPos, float yPos) {
            try {
                renderHelperMethod.invoke(card, sb, color, region, xPos, yPos);
            } catch (IllegalArgumentException | SecurityException | InvocationTargetException | IllegalAccessException var7) {// 322
                var7.printStackTrace();
            }

        }

        static {
            try {
                renderHelperMethod = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, Float.TYPE, Float.TYPE);
                renderHelperMethod.setAccessible(true);
            } catch (NoSuchMethodException var1) {
                var1.printStackTrace();
            }

        }
    }
}
