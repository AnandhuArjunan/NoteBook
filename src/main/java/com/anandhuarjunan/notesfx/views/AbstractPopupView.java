package com.anandhuarjunan.notesfx.views;

import java.util.function.Function;

import com.airhacks.afterburner.views.FXMLView;



public class AbstractPopupView extends FXMLView{

	public AbstractPopupView(Function<String, Object> injectionContext) {
		super(injectionContext);
	}

}
