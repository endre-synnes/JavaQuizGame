package com.endre.java.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.function.Supplier;

@Singleton
@Startup
public class DefaultDataInitializerEjb {

    @EJB
    private CategoryEjb categoryEjb;

    @EJB
    private QuizEjb quizEjb;

    @PostConstruct
    public void initialize(){
        Long ctgC = attempt(() -> categoryEjb.createCategory("Cars"));
        Long ctgH = attempt(() -> categoryEjb.createCategory("History"));

        Long sCB = attempt(() -> categoryEjb.createSubCategory(ctgC, "Car Brands"));
        Long sCH = attempt(() -> categoryEjb.createSubCategory(ctgC, "Car History"));
        Long sCS = attempt(() -> categoryEjb.createSubCategory(ctgC, "Car specifications"));

        Long sRE = attempt(() -> categoryEjb.createSubCategory(ctgH, "Roman Empire"));
        Long sWW2 = attempt(() -> categoryEjb.createSubCategory(ctgH, "World War 2"));

        createCarBrandQuestions(sCB);
        createCarHistoryQuestions(sCH);
        createCarSpecificationQuestions(sCS);
        createRomanEmpireQuestions(sRE);
        createWorldWar2Questions(sWW2);

    }

    private void createCarBrandQuestions(Long sub) {
        createQuiz(
                sub,
                "what car brand did make a model called beatle?",
                "BMW",
                "Volks Wagen",
                "Ford",
                "Porsche",
                1);

    }

    private void createCarHistoryQuestions(Long sub) {
        createQuiz(
                sub,
                "What car (BRAND) does Steve Mcqueen drive in the Movie 'Bullitt'?",
                "Ford",
                "Chevrolet",
                "Cadillac",
                "GM",
                0);

        createQuiz(
                sub,
                "What manufacturer has the most 24 hours of le mans wins?",
                "Audi",
                "Alfa Romeo",
                "Ferrari",
                "Porsche",
                3);
    }

    private void createCarSpecificationQuestions(Long sub) {
        createQuiz(
                sub,
                "How many horse power does Bugatti Chiron have?",
                "993",
                "1001",
                "1479",
                "1589",
                2);

    }

    private void createRomanEmpireQuestions(Long sub) {
        createQuiz(
                sub,
                "Who was the first Roman Emperor?",
                "Caligula",
                "Tiberius",
                "Augustus",
                "Caesar",
                2);

        createQuiz(
                sub,
                "After which god is the month 'Mars' named?",
                "God of Thunders",
                "God of Love",
                "God of Wars",
                "God of Pestilence",
                2);

        createQuiz(
                sub,
                "According to the legend, who founded Rome ?",
                "Romulus and Remus",
                "Augustus and Caesar",
                "Tiberius and Claudius",
                "Erik and Olav",
                0);

        createQuiz(
                sub,
                "Which was the largest empire in history?",
                "Mongol Empire",
                "Russian Empire",
                "English Empire",
                "Roman Empire",
                2);

        createQuiz(
                sub,
                "Who were the Praetorians?",
                "Priests of the God Pratunus",
                "Slaves",
                "Barbarians",
                "Elite soldiers",
                3);

    }

    private void createWorldWar2Questions(Long sub) {
        createQuiz(
                sub,
                "What year did World War 2 start?",
                "1938",
                "1939",
                "1945",
                "1941",
                1);

        createQuiz(
                sub,
                "Who was the leader of The Soviet Uion during World War 2?",
                "Hitler",
                "Stalin",
                "Churchill",
                "Lenin",
                1);
    }


    private void createQuiz(long subCategoryId,
                            String question,
                            String firstAnswer,
                            String secondAnswer,
                            String thirdAnswer,
                            String fourthAnswer,
                            int indexOfCorrectAnswer){
        attempt(() -> quizEjb.createQuiz(
                subCategoryId,
                question,
                firstAnswer,
                secondAnswer,
                thirdAnswer,
                fourthAnswer,
                indexOfCorrectAnswer));
    }


    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }

}
