@complete @comments
Feature: Make comments on posts
  As a developer
  I want to use the comments endpoint to create, read, update and delete comments
  So that I can create new comments, get existing comments, update comments, replace comments and delete comments

  @comments1
  Scenario: Successfully make a comment on a post
    Given I create a post
      | id | userId | title              | body                   |
      |  1 |      1 | CREATED POST TITLE | CREATED POST BODY TEXT |
    When I create a comment on the post with id "1"
      | postId | name              | email                            | body              |
      |      1 | TEST COMMENT NAME | testcomment.user@testcomment.com | test comment body |
    And I fetch the comment with id "1"
    Then the comment should contain the following
      | postId | name              | email                            | body              |
      |      1 | TEST COMMENT NAME | testcomment.user@testcomment.com | test comment body |

  @comments2
  Scenario: Should not be able to create a comment with no post id
    When I create a comment
      | postId | name | email              | body      |
      |        | TEST | test.user@test.com | TEST BODY |
    Then the request should fail
