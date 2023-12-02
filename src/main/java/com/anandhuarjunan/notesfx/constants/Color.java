package com.anandhuarjunan.notesfx.constants;

public enum Color {

	
	RED("Red","#B71C1C","getMaterialRedColor",""),
	PINK("Pink","#880E4F","getMaterialPinkColor",""),
	PURPLE("Purple","#4A148C","getMaterialPurpleColor",""),
	DEEP_PURPLE("Deep Purple","#311B92","getMaterialDarkPurpleColor",""),
	INDIGO("Indigo","#1A237E","getMaterialIndigoColor",""),
	BLUE("Blue","#0D47A1","getMaterialBlueColor",""),
	LIGHT_BLUE("Light Blue","#01579B","getMaterialLightBlueColor",""),
	CYAN("Cyan","#006064","getMaterialCyanColor",""),
	TEAL("Teal","#004D40","getMaterialTealColor",""),
	GREEN("Green","#1B5E20","getMaterialGreenColor",""),
	LIGHT_GREEN("Light Green","#33691E","getMaterialLightGreenColor",""),
	LIME("Lime","#827717","getMaterialLimeColor",""),
	YELLOW("Yellow","#F57F17","getMaterialYellowColor",""),
	AMBER("Amber","#FF6F00","getMaterialAmberColor",""),
	ORANGE("Orange","#E65100","getMaterialOrangeColor",""),
	DEEP_ORANGE("Deep Orange","#BF360C","getMaterialDeepOrangeColor",""),
	BROWN("Brown","#3E2723","getMaterialBrownColor",""),
	GREY("Grey","#212121","getMaterialGreyColor",""),
	BLUE_GREY("BLue Grey","#263238","getMaterialLightBlueColor","");

	
	String name; String colour;String shadeMethod; String accentMethod;
	
	Color(String name, String colour, String shadeMethod, String accentMethod) {
		this.name = name;
		this.colour = colour;
		this.shadeMethod = shadeMethod;
		this.accentMethod = accentMethod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getShadeMethod() {
		return shadeMethod;
	}

	public void setShadeMethod(String shadeMethod) {
		this.shadeMethod = shadeMethod;
	}

	public String getAccentMethod() {
		return accentMethod;
	}

	public void setAccentMethod(String accentMethod) {
		this.accentMethod = accentMethod;
	}
	
	
	
	
}
