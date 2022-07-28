package com.brandon.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.brandon.parstagram.Post
import com.brandon.parstagram.PostAdapter
import com.brandon.parstagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView

    lateinit var swipeContainer: SwipeRefreshLayout

    lateinit var adapter: PostAdapter

    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
/*
        swipeContainer = view.findViewById(R.id.swipeContainer)
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            fetchTimelineAsync(0)
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)
        */
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRecyclerView = view.findViewById(R.id.postRecyclerView)

        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter

        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }
    /*
    fun fetchTimelineAsync(page: Integer) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        client.getHomeTimeline(object: JsonHttpResponseHandler() {
             fun onSuccess(statusCode: Int, headers: okhttp3.Headers, json: JSON) {
                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear()
                // ...the data has come back, add new items to your adapter...
                adapter.addAll()
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false)
            }

            fun onFailure(
                statusCode: Int,
                headers: okhttp3.Headers,
                response: String,
                throwable: Throwable
            ) {
                Log.d("DEBUG", "Fetch timeline error", throwable)
            }
        })
    }

     */

    open fun queryPosts(){
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if(e != null){
                    Log.e("Feed","Error fetching feed")
                }else{
                    if (posts != null){
                        for (post in posts){
                            Log.i("Feed","Post: "+ post.getDestription() + " , username: "+ post.getUser()?.username)
                        }
                        Log.i("Feed","is Here")
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    // Clean all elements of the recycler
    fun clear() {
        allPosts.clear()
        adapter.notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(posts: List<Post>) {
        allPosts.addAll(posts)
        adapter.notifyDataSetChanged()
    }


}