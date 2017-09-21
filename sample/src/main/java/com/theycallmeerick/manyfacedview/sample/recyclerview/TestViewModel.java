package com.theycallmeerick.manyfacedview.sample.recyclerview;

import com.theycallmeerick.manyfacedview.view.ViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestViewModel {
    private static String[] TEXT = new String[]{
            "Samurai Jack",
            "Mojo Jojo",
            "Marceline",
            "Dexter",
            "Red Guy",
            "Plank",
            "Courage",
            "Kid Goku"

    };
    private static String[] URLS = new String[]{
            "http://i0.kym-cdn.com/entries/icons/original/000/013/391/3658935-samurai_jack.jpg",
            "http://statici.behindthevoiceactors.com/behindthevoiceactors/_img/chars/mojo-jojo-teen-titans-go-7.54.jpg",
            "http://orig12.deviantart.net/4fae/f/2012/084/3/2/marceline_by_mirosdarko-d4txweu.png",
            "https://vignette.wikia.nocookie.net/dexterslab/images/4/45/Clip-art-dexters-laboratory-660349.jpg/revision/latest?cb=20120417000243",
            "https://vignette2.wikia.nocookie.net/cow-and-chicken/images/e/ef/The_Red_Guy.jpg/revision/latest?cb=20140204163038",
            "https://vignette.wikia.nocookie.net/edwikia/images/8/88/Wee_Roaches.jpg/revision/latest?cb=20100722013857",
            "https://vignette1.wikia.nocookie.net/parody/images/a/a0/Courage_dog_scream.jpg/revision/latest?cb=20150910064108",
            "http://vignette2.wikia.nocookie.net/dragonball/images/7/79/Dragon_ball_kid_goku_8_by_superjmanplay2-d4f3i9r.png/revision/latest?cb=20111126213826"
    };

    public @ViewState int viewState = ViewState.LOADING;
    public String text;
    public String imageUrl;

    private TestViewModel() {
    }

    public static List<TestViewModel> createRandomElements(int quantity) {
        Random random = new Random();
        List<TestViewModel> elements = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int index = Math.abs(random.nextInt() % TEXT.length);
            elements.add(instance(TEXT[index], URLS[index]));
        }

        return elements;
    }

    private static TestViewModel instance(String text, String url) {
        TestViewModel viewModel = new TestViewModel();
        viewModel.text = text;
        viewModel.imageUrl = url;

        return viewModel;
    }
}
