package Apec.Components.Gui.GuiIngame.GuiElements;

import Apec.ApecMain;
import Apec.ApecUtils;
import Apec.Components.Gui.GuiIngame.GUIComponentID;
import Apec.DataExtractor;
import Apec.Settings.SettingID;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.vector.Vector2f;

public class MnBar extends GUIComponent {

    public MnBar() {
        super(GUIComponentID.MN_BAR);
    }

    @Override
    public void drawTex(DataExtractor.PlayerStats ps, DataExtractor.ScoreBoardData sd, DataExtractor.OtherData od, ScaledResolution sr,boolean editingMode) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,scale);
        if (ApecMain.Instance.settingsManager.getSettingState(SettingID.MP_BAR)) {
            GuiIngame gi = Minecraft.getMinecraft().ingameGUI;

            Vector2f StatBar = this.getAnchorPointPosition(sr);
            StatBar = ApecUtils.addVec(StatBar, delta_position);

            float mpFactor = ps.Mp > ps.BaseMp ? 1 :(float) ps.Mp / (float) ps.BaseMp;

            mc.renderEngine.bindTexture(new ResourceLocation(ApecMain.modId, "gui/statBars.png"));

            gi.drawTexturedModalRect((int) StatBar.x/scale, (int) StatBar.y/scale, 0, 10, 182, 5);
            gi.drawTexturedModalRect((int) StatBar.x/scale, (int) StatBar.y/scale, 0, 15, (int) (mpFactor * 182f), 5);
        }
        GlStateManager.popMatrix();

    }

    @Override
    public void draw(DataExtractor.PlayerStats ps, DataExtractor.ScoreBoardData sd,DataExtractor.OtherData od, ScaledResolution sr,boolean editingMode) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,scale);
        if (ApecMain.Instance.settingsManager.getSettingState(SettingID.MP_BAR)) {
            Vector2f StatBar = this.getAnchorPointPosition(sr);

            StatBar = ApecUtils.addVec(StatBar, delta_position);

            String MPString = (ps.IsAbilityShown && ApecMain.Instance.settingsManager.getSettingState(SettingID.SHOW_ABILITY_TEXT) ? ps.AbilityText + "\u00a7r ": "") + ps.Mp + "/" + ps.BaseMp + " MP";
            ApecUtils.drawThiccBorderString(MPString, (int)(StatBar.x/scale + 112 + 70 - mc.fontRendererObj.getStringWidth(MPString)), (int)(StatBar.y/scale - 10), 0x1139bd);
        }
        GlStateManager.popMatrix();
    }

    @Override
    public Vector2f getAnchorPointPosition(ScaledResolution sr) {
        return new Vector2f(sr.getScaledWidth() - 190, 34);
    }

    @Override
    public Vector2f getBoundingPoint() {
        return ApecUtils.addVec(getRealAnchorPoint(new ScaledResolution(mc)),new Vector2f(182*scale,5*scale));
    }

}