package mjaroslav.mcmods.thaumores.common.research;

import mjaroslav.mcmods.thaumores.common.event.TOEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;

public class ResearchItemCopy extends ResearchItem {
	private String copyKey;

	public ResearchItemCopy(String copyKey, String key, String category) {
		super(key, category);
		this.copyKey = copyKey;
		this.setStub();
		TOEvents.researchCopyList.add(copyKey + ":" + key);
		this.setParents(copyKey);
		this.setConcealed();
		this.setHidden();
		this.setPages(ResearchCategories.getResearch(copyKey).getPages());
	}

	public ResearchItemCopy(String copyKey, String key, String category, int col, int row, ResourceLocation icon) {
		super(key, category, new AspectList(), col, row, 1, icon);
		this.copyKey = copyKey;
		this.setStub();
		TOEvents.researchCopyList.add(copyKey + ":" + key);
		this.setParents(copyKey);
		this.setConcealed();
		this.setHidden();
		this.setPages(ResearchCategories.getResearch(copyKey).getPages());
	}

	public ResearchItemCopy(String copyKey, String key, String category, int col, int row, ItemStack icon) {
		super(key, category, new AspectList(), col, row, 1, icon);
		this.copyKey = copyKey;
		this.setStub();
		TOEvents.researchCopyList.add(copyKey + ":" + key);
		this.setParents(copyKey);
		this.setConcealed();
		this.setHidden();
		this.setPages(ResearchCategories.getResearch(copyKey).getPages());
	}

	@Override
	public String getName() {
		return StatCollector.translateToLocal("tc.research_name." + copyKey);
	}

	@Override
	public String getText() {
		return StatCollector.translateToLocal("tc.research_text." + copyKey);
	}
}
