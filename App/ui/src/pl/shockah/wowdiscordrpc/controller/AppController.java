package pl.shockah.wowdiscordrpc.controller;

import javax.annotation.Nonnull;

import javafx.scene.Scene;
import pl.shockah.unicorn.javafx.Controller;

public class AppController extends Controller {
	@Override
	protected void onLoaded() {
		super.onLoaded();

		createObservables();
		setupViews();
		setupBindings();
	}

	private void createObservables() {
	}

	private void setupViews() {
	}

	private void setupBindings() {
	}

	@Override
	protected void onAddedToScene(@Nonnull Scene scene) {
		super.onAddedToScene(scene);
	}
}