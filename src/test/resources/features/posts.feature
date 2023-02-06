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
