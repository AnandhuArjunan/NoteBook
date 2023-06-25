package com.anandhuarjunan.notesfx.views;

import java.util.function.Function;

import com.airhacks.afterburner.views.FXMLView;

public class NoteView extends FXMLView{
	public NoteView(Function<String, Object> injectionContext) {
		super(injectionContext);
	}
}
