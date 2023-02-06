@complete @posts
Feature: Make posts
  As a developer
  I want to use the posts endpoint to create, read, update and delete posts
  So that I can create new posts, get existing posts, update posts, replace entire posts and delete posts

  @posts1
  Scenario: Successfully create a post
    Given I create a post
      | userId | title              | body                   |
      |      1 | CREATED POST TITLE | CREATED POST BODY TEXT |
    When I fetch the post with id "1"
    Then the post title should be "CREATED POST TITLE"
    And the post body should be "CREATED POST BODY TEXT"

  @posts2
  Scenario: Should not be able to create post with id as id should be read-only
    Given I create a post
      | id | userId | title              | body                   |
      |    |      1 | CREATED POST TITLE | CREATED POST BODY TEXT |
    Then the request should fail

  @posts3
  Scenario: Should not be able to create a post with no user id
    Given I create a post
      | id | userId | title              | body                   |
      |  1 |        | CREATED POST TITLE | CREATED POST BODY TEXT |
    Then the request should fail

  @posts4
  Scenario: Should not be able to create a post with no title
    Given I create a post
      | id | userId | title | body                   |
      |  1 |      1 |       | CREATED POST BODY TEXT |
    Then the request should fail

  @posts5
  Scenario: Should not be able to create a post with no body
    Given I create a post
      | id | userId | title              | body |
      |  1 |      1 | CREATED POST TITLE |      |
    Then the request should fail

  @posts6
  Scenario: Get all posts
    When I fetch all posts
    Then all the posts should contain the following fields
      | id     |
      | userId |
      | title  |
      | body   |

  @posts7
  Scenario: Get a post by id
    When I fetch the post with id "1"
    Then it should contain the following fields
      | id     |
      | userId |
      | title  |
      | body   |
    And the id should be "1"
    Then the post title should be "CREATED POST TITLE"
    And the post body should be "CREATED POST BODY TEXT"

  @posts8
  Scenario: Get all posts by user id
    When I fetch all posts by a user with id "1"
    Then all the posts should contain the following fields
      | id     |
      | userId |
      | title  |
      | body   |
    And all the posts should have user id "1"

  @posts9
  Scenario: Get post by title
    When I fetch all the posts with title "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
    Then all the posts should contain the following fields
      | id     |
      | userId |
      | title  |
      | body   |
    And all the posts should have title "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"

  @posts10
  Scenario: Update fields on a post
    When I update the post with id "1" with the following
      | userId | title              | body                   |
      |      9 | UPDATED POST TITLE | UPDATED POST BODY TEXT |
    And I fetch the post with id "1"
    And the user id should be "9"
    Then the post title should be "UPDATED POST TITLE"
    And the post body should be "UPDATED POST BODY TEXT"

  @posts11
  Scenario: Edit an entire post
    When I edit the post with id "1" with new details
      | userId | title             | body                  |
      |     11 | EDITED POST TITLE | EDITED POST BODY TEXT |
    And I fetch the post with id "1"
    And the user id should be "11"
    Then the post title should be "EDITED POST TITLE"
    And the post body should be "EDITED POST BODY TEXT"

  @posts12
  Scenario: Delete a post
    When I delete the post with id "1"
    And I fetch the post with id "1"
    Then the post with id "1" should be deleted
