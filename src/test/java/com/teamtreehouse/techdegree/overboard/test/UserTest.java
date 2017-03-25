package com.teamtreehouse.techdegree.overboard.test;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import com.teamtreehouse.techdegree.overboard.model.Answer;
import com.teamtreehouse.techdegree.overboard.model.Board;
import com.teamtreehouse.techdegree.overboard.model.Question;
import com.teamtreehouse.techdegree.overboard.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by scott on 3/24/2017.
 */
public class UserTest {
    private Board board;
    private User Scotty, Craig, comedian, PeanutGallery, SillySally;
    private Question joke;
    private Answer punchline;
    @Before
    public void setUp(){
         board = new Board("BadJoke");
         Scotty = board.createUser("Scotty");
         Craig = board.createUser("Craig");
         PeanutGallery = board.createUser("Peanut gallery");
         comedian = board.createUser("comedian");
         SillySally = board.createUser("SillySally");
         joke = Scotty.askQuestion("How many South Americans does it take to change a lightbulb?");
         punchline = comedian.answerQuestion(joke,"A Brazilian");
    }

    @Test
    public void UserReputationIncrementsByFiveOnQuestionUpvote(){
        Craig.upVote(joke);
        assertEquals(Scotty.getReputation(),5);
    }

    @Test
    public void UserReputationIncrementsByTenOnAnswerUpvote(){
        SillySally.upVote(punchline);
        assertEquals(comedian.getReputation(),10);
    }

    @Test
    public void UserReputationIncrementsByFifteenOnAcceptedAnswer(){
        Scotty.acceptAnswer(punchline);
        assertEquals(comedian.getReputation(),15);

    }

    @Test(expected = VotingException.class)
    public void UserCanNotUpvoteOwnAnswer(){
        comedian.upVote(punchline);
    }

    @Test(expected = VotingException.class)
    public void UserCanNotUpvoteOwnQuestion(){
        Scotty.upVote(joke);
    }

    @Test(expected = VotingException.class)
    public void UserCanNotDownvoteOwnAnswer(){
        comedian.downVote(punchline);
    }

    @Test(expected = VotingException.class)
    public void UserCanNotDownvoteOwnQuestion(){
        Scotty.downVote(joke);
    }

    @Test(expected = AnswerAcceptanceException.class)
    public void UserCanNotAcceptAnswerForAnotherUserQuestion() throws Exception {
        comedian.acceptAnswer(punchline);

    }

    @Test
    public void DownVotingAnAnswerRemovesOneReputation() throws Exception {
        Scotty.acceptAnswer(punchline);
        assertEquals(comedian.getReputation(),15);
        PeanutGallery.downVote(punchline);
        assertEquals(comedian.getReputation(),14);
    }
}