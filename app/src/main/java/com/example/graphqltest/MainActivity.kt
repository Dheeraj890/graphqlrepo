package com.example.graphqltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.android.synthetic.main.repo_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var client: setupApollo


    init {
        client = setupApollo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        button_find.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            client.getClient().query(FindQuery
                .builder()
                .name(repo_name_edittext.text.toString())
                .owner(owner_name_edittext.text.toString())
                .build())
                .enqueue(object : ApolloCall.Callback<FindQuery.Data>() {

                    override fun onFailure(e: ApolloException) {
                        Log.d("exception ",e.message.toString())

                        progress_bar.visibility = View.GONE
                    }

                    override fun onResponse(response: Response<FindQuery.Data>) {
                        Log.d(" " + response.data()?.repository(),"")
                        runOnUiThread {
                            progress_bar.visibility = View.GONE
                            name_text_view.text = String.format(getString(R.string.name_text),
                                response.data()?.repository()?.name())
                            description_text_view.text = String.format(getString(R.string.description_text),
                                response.data()?.repository()?.description())
//                            forks_text_view.text = String.format(getString(R.string.fork_count_text),
//                                response.data()?.repository()?.forkCount().toString())
//                            url_text_view.text = String.format(getString(R.string.url_count_text),
//                                response.data()?.repository()?.url().toString())
                        }
                    }

                })
        }


    }
}
