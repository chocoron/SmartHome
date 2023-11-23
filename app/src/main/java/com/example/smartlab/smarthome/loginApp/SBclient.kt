package com.example.smartlab.smarthome.loginApp

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SBclient {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://kbzpcwisyrmgxojrjgzz.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtienBjd2lzeXJtZ3hvanJqZ3p6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDAxODY4MTYsImV4cCI6MjAxNTc2MjgxNn0.g8mGdxFfoCQJmUoVmgPZcbG50gO5zoXyMyy5MXx-EW8"
    ) {
        install(GoTrue)
        install(Postgrest)
        //install other modules
    }
    public fun getClient(): SupabaseClient {
        return supabase
    }
}