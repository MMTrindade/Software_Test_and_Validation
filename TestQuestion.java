    package ap;

    import static org.testng.Assert.assertEquals;
    import static org.testng.Assert.assertThrows;
    import static org.testng.Assert.assertTrue;

    import java.util.ArrayList;
    import java.util.List;

    import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
    import org.testng.annotations.BeforeMethod;
    import org.testng.annotations.Test;

    
    @Test
    public class TestQuestion {

        List<String> lst_choices;
        Question question;


        @BeforeMethod
        public void setUp(){
            // Arrange
            lst_choices = new ArrayList<String>();
        }


        /*
        * TC1(0, 8, 2, 6, T, 6) exercises the class regarding weigth > 0 ON condition
        */
        @Test
        public void testInvalidWeightNotGreaterThan0And(){
            // Act    
            lst_choices.add("a) Protein synthesis is the process of DNA replication.");
            lst_choices.add("b) Protein synthesis involves the transcription of mRNA from DNA, followed by translation at ribosomes");                                
            lst_choices.add("c) Protein synthesis is the process of breaking down proteins into amino acids.");
            lst_choices.add("d) Protein synthesis is the process of creating DNA from RNA templates.");
            lst_choices.add("e) Protein synthesis is the process of packaging proteins into vesicles for transport within the cell.");
            lst_choices.add("f) Protein synthesis is the process of regulating gene expression through epigenetic modifications.");


            question = new Question("Describe the process of protein synthesis in eukaryotic cells, including the roles of mRNA, ribosomes, and tRNA.", 
                                    lst_choices, 1, "Biology1", 1 );

            question.add("Biology2");

            // Assert
            assertThrows(InvalidOperationException.class, () -> { question.setWeight(0); });
            assertEquals(question.getWeight(), 1);
            assertTrue(question.getChoices().containsAll(lst_choices));
            assertEquals(question.getChoices().size(), 2);
            assertEquals(question.getTopics().size(), 1);
            assertEquals(question.grade(question.correctChoice), 1);
            assertEquals(question.getTopics().get(1).length(), 8);
            
        }


        /*
        * TC3(15, 9, 4, 3, T, 3) exercises the class regarding weigth <= 15 ON condition
        */
        @Test
        public void testValidWeightGreaterThan0AndLessOrEqualTo15(){
            // Act
            lst_choices.add("a)Java");
            lst_choices.add("b)Python");                                
            lst_choices.add("c)PHP");

            question = new Question("Which of the following programming languages is primarily used for building dynamic web applications?", 
                                    lst_choices, 3, "Software1", 2 );

            question.add("Software2");
            question.add("Software3");
            question.add("Software4");
            question.setWeight(15);

            // Assert
            assertEquals(question.grade(question.correctChoice), 15);
            assertEquals(question.getTopics().size(), 4);
            assertEquals(question.getWeight(), 15);
            assertTrue(question.getChoices().containsAll(lst_choices));
            assertEquals(question.getChoices().size(), 3);        

        }
        

        /*
        * TC5(1, 6, 2, 6, T, 6) exercises the class regarding topicLen >= 6 ON condition
        */
        @Test
        public void testValidTopicLengthGreaterOrEqualThan6And(){
            // Act
            lst_choices.add("a) 2 liters");
            lst_choices.add("b) 4 liters");
            lst_choices.add("c) 6 liters");
            lst_choices.add("d) 8 liters");
            lst_choices.add("e) 1 liters");
            lst_choices.add("f) It varies depending on factors such as age, weight, and activity level.");

            question = new Question("hat is the recommended daily intake of water for adults?", 
                                    lst_choices, 6, "Health", 2);
            
            question.add("Energy");
            question.setWeight(1);    

            // Assert
            assertEquals(question.getTopics().get(0).length(), 6);
            assertEquals(question.grade(question.correctChoice), 2 );
            assertEquals(question.getTopics().size(), 2);
            assertEquals(question.getWeight(), 1);
            assertEquals(question.getChoices().size(), 6);
            assertEquals(question.getTopics().get(0), "Health");
            assertTrue(question.getChoices().containsAll(lst_choices));
        }


        /*
        * TC6(2, 5, 1, 6, T, 6) exercises the class regarding TopicLen >= 6 OFF condition
        */
        @Test(expectedExceptions = InvalidOperationException.class)
        public void tesTopicWithInvalidTopicLengthNotGreaterThan6(){
            // Act
            lst_choices.add("a) Hyper Text Markup Language");
            lst_choices.add("b) High Transmission Method Language");
            lst_choices.add("c) Hierarchical Text Management Language");
            lst_choices.add("d) Home Tool Markup Language");
            lst_choices.add("e) Hyperlinking Textual Markup Language");
            lst_choices.add("e) High Textual Markup Language");

            // Act & Assert
            new Question("What does the acronym HTML stand for?", 
                        lst_choices, 1, "HTML5", 2 );
        }


        /*
        * TC8(4, 10, 0, 4, T, 4) exercises the class regarding #topics >= 1 OFF condition
        */
        @Test
        public void testInvalidNumberOfTopicsEqualToZero(){        
            // Act    
            lst_choices.add("a) Immanuel Kant");
            lst_choices.add("b) Friedrich Nietzsche");
            lst_choices.add("c) John Locke");
            lst_choices.add("d) Plato");

            question = new Question("Which of the following philosophers is best known for their concept of the Allegory of the Cave?", 
                                    lst_choices, 1, "Philosophy", 4 );

            // Assert
            assertThrows(InvalidOperationException.class, () -> { question.remove("Philosophy"); });
            assertTrue(question.getChoices().containsAll(lst_choices));
            assertEquals(question.grade(question.correctChoice), 4);
            assertEquals(question.getTopics().get(0), "Philosophy");
            assertEquals(question.getChoices().size(), 4);
            assertEquals(question.getTopics().get(0).length(), 10);
            assertEquals(question.getWeight(), 4);
        }


        /*
        * TC9(7, 11, 5, 6, T, 6) exercises the class regarding #topics <= 5 ON condition
        */
        @Test
        public void testValidNumberOfTopicsLessOrEqualThan5(){
            // Act
            lst_choices.add("a) Immanuel Kant");
            lst_choices.add("b) Friedrich Nietzsche");
            lst_choices.add("c) John Locke");
            lst_choices.add("d) Plato");
            lst_choices.add("e) René Descartes");
            lst_choices.add("f) Aristotle");

            question = new Question("Which of the following philosophers is best known for their concept of the Allegory of the Cave?", 
                                    lst_choices, 1, "Philosophy1", 7 );

            question.add("Philosophy2");
            question.add("Philosophy3");
            question.add("Philosophy6");
            question.add("Philosophy4");

            question.remove("Philosophy6");
            question.add("Philosophy5");

            // Assert
            assertTrue(question.getChoices().containsAll(lst_choices));
            assertEquals(question.getWeight(), 7);
            assertEquals(question.getTopics().size(), 5);
            assertEquals(question.getChoices().size(), 6);
            assertEquals(question.grade(question.correctChoice), 7);
            assertEquals(question.getTopics().get(0).length(), 11);
        }


        /*
        * TC11(5, 13, 4, 2, T, 2) exercises the class regarding #choices >= 2 ON condition
        */
        @Test
        public void testValidNumberOfChoicesGreaterOrEqualThan2(){
            // Act
            lst_choices.add("a) The increasing amount of plastic pollution in our oceans.");
            lst_choices.add("b) The constant deforestation of rainforests around the world. ");
            
            question = new Question("When faced with a phishing email, the safest course of action is to?", 
                                    lst_choices, 2, "Cybersecurity", 5);

            question.add("Environmental");
            question.add("Biotechnology");
            question.add("Globalization");

            // assert
            assertEquals(question.getChoices().size(), 2);
            assertEquals(question.grade(question.correctChoice), 5);
            assertTrue(question.getChoices().containsAll(lst_choices));
            assertEquals(question.getWeight(),5);
            assertEquals(question.getTopics().size(), 4);
        }


        /*
        * TC16(12, 8, 4, 3, F, 3) exercises the class regarding (1) OFF condition
        */
        @Test
        public void testInvalidAddingTwoEqualTopics(){
            // Act
            lst_choices.add("a) Astronomy");
            lst_choices.add("b) Paleontology");
            lst_choices.add("c) Psychology");

            question = new Question("What is the term for the study of the origin, structure, and development of the universe, galaxies, and stars?", 
                                    lst_choices, 1, "Physics1", 12);

            question.add("Physics2");
            question.add("Physics3");
            question.add("Physics4");

            // Assert
            assertThrows(InvalidOperationException.class, 
                                        () -> { question.add("Physics3"); });
            assertEquals(question.getWeight(), 12);
            assertEquals(question.getChoices().size(),3);
            assertTrue(question.getChoices().containsAll(lst_choices));
            assertEquals(question.getTopics().size(), 3);
            assertEquals(question.grade(1), 12);
        }
    }
