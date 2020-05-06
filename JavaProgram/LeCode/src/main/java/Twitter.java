import java.util.*;

public class Twitter {

    private List<Integer> users = new ArrayList<Integer>();

    private Map<Integer, List<Integer>> followsMap = new HashMap<Integer, List<Integer>>();

    private Map<Integer, List<Tweet>> userPostMap = new HashMap<Integer, List<Tweet>>();



    /** Initialize your data structure here. */
    public Twitter() {

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        Date date = new Date();
        Tweet tweet = new Tweet(userId, null, tweetId, date.getTime());
        if (userPostMap.get(userId) == null) {
            List<Tweet> tweets = new ArrayList<Tweet>();
            userPostMap.put(userId, tweets);
        }
        List<Tweet> tweets = userPostMap.get(userId);
        tweets.add(tweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Tweet> tweets = userPostMap.get(userId);
        Collections.sort(tweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet tweet, Tweet t1) {
                if (tweet.getTime() - t1.getTime() <= 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        List<Integer> nowFollowers = new ArrayList<>();
        for (Tweet tweet : tweets) {
            nowFollowers.add(tweet.getPoster());
        }
        List<Integer> follows = followsMap.get(userId);
        Iterator<Integer> followsIterators = follows.iterator();
        while (followsIterators.hasNext()) {
            Integer follower = followsIterators.next();

        }
        return null;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (followsMap.get(followeeId) == null) {
            List<Integer> followees = new ArrayList<Integer>();
            followsMap.put(followerId, followees);
        }
        List<Integer> followees = followsMap.get(followerId);
        followees.add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        List<Integer> followees = followsMap.get(followerId);
        followees.remove(followeeId);
    }

    private static class Tweet {
        private int poster;

        private int accepter;

        private int tweetId;

        private long time;



        public Tweet(Integer poster, Integer accepter, Integer tweetId, Long time) {
            this.poster = poster;

            this.accepter = accepter;

            this.tweetId = tweetId;

            this.time = time;
        }

        public int getPoster() {
            return poster;
        }

        public void setPoster(int poster) {
            this.poster = poster;
        }

        public int getAccepter() {
            return accepter;
        }

        public void setAccepter(int accepter) {
            this.accepter = accepter;
        }

        public int getTweetId() {
            return tweetId;
        }

        public void setTweetId(int tweetId) {
            this.tweetId = tweetId;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }

    public static void main(String[] args) {

    }
}
