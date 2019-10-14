package com.rrzaniolo.restaurantchallenge.data.repositories

import com.rrzaniolo.restaurantchallenge.data.models.RestaurantListResponse
import com.rrzaniolo.restaurantchallenge.di.configurations.Database
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.repositories.RestaurantRepository
import com.squareup.moshi.Moshi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
class RestaurantRepositoryImp(private val moshi: Moshi, private val dataBase: Database):
    RestaurantRepository {
    private fun readJsonFile(jsonInputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()

        val buffer = ByteArray(1024)
        var length = 0
        try {
            while (length != -1) {
                length = jsonInputStream.read(buffer)
                outputStream.write(buffer, 0, length)
            }
            outputStream.close()
            jsonInputStream.close()
        } catch (exception: IOException) {}

        return outputStream.toString()
    }

    override fun getRestaurants(): Flowable<RestaurantListResponse> {
//        val jsonFile = Resources.getSystem().openRawResource(R.raw.restaurants)
//        val jsonString = readJsonFile(jsonFile)

        val jsonString = "{\n" +
                "  \"restaurants\": [{\n" +
                "    \"name\": \"Tanoshii Sushi\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 0.0,\n" +
                "      \"newest\": 96.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 1190,\n" +
                "      \"popularity\": 17.0,\n" +
                "      \"averageProductPrice\": 1536,\n" +
                "      \"deliveryCosts\": 200,\n" +
                "      \"minCost\": 1000\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Tandoori Express\",\n" +
                "    \"status\": \"closed\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 1.0,\n" +
                "      \"newest\": 266.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 2308,\n" +
                "      \"popularity\": 123.0,\n" +
                "      \"averageProductPrice\": 1146,\n" +
                "      \"deliveryCosts\": 150,\n" +
                "      \"minCost\": 1300\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Royal Thai\",\n" +
                "    \"status\": \"order ahead\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 2.0,\n" +
                "      \"newest\": 133.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 2639,\n" +
                "      \"popularity\": 44.0,\n" +
                "      \"averageProductPrice\": 1492,\n" +
                "      \"deliveryCosts\": 150,\n" +
                "      \"minCost\": 2500\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Sushi One\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 3.0,\n" +
                "      \"newest\": 238.0,\n" +
                "      \"ratingAverage\": 4.0,\n" +
                "      \"distance\": 1618,\n" +
                "      \"popularity\": 23.0,\n" +
                "      \"averageProductPrice\": 1285,\n" +
                "      \"deliveryCosts\": 0,\n" +
                "      \"minCost\": 1200\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Roti Shop\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 4.0,\n" +
                "      \"newest\": 247.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 2308,\n" +
                "      \"popularity\": 81.0,\n" +
                "      \"averageProductPrice\": 915,\n" +
                "      \"deliveryCosts\": 0,\n" +
                "      \"minCost\": 2000\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Aarti 2\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 5.0,\n" +
                "      \"newest\": 153.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 1605,\n" +
                "      \"popularity\": 44.0,\n" +
                "      \"averageProductPrice\": 922,\n" +
                "      \"deliveryCosts\": 250,\n" +
                "      \"minCost\": 500\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Pizza Heart\",\n" +
                "    \"status\": \"order ahead\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 6.0,\n" +
                "      \"newest\": 118.0,\n" +
                "      \"ratingAverage\": 4.0,\n" +
                "      \"distance\": 2453,\n" +
                "      \"popularity\": 9.0,\n" +
                "      \"averageProductPrice\": 1103,\n" +
                "      \"deliveryCosts\": 150,\n" +
                "      \"minCost\": 1500\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Mama Mia\",\n" +
                "    \"status\": \"order ahead\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 7.0,\n" +
                "      \"newest\": 250.0,\n" +
                "      \"ratingAverage\": 4.0,\n" +
                "      \"distance\": 1396,\n" +
                "      \"popularity\": 6.0,\n" +
                "      \"averageProductPrice\": 912,\n" +
                "      \"deliveryCosts\": 0,\n" +
                "      \"minCost\": 1000\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Feelfood\",\n" +
                "    \"status\": \"order ahead\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 8.0,\n" +
                "      \"newest\": 163.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 2732,\n" +
                "      \"popularity\": 31.0,\n" +
                "      \"averageProductPrice\": 902,\n" +
                "      \"deliveryCosts\": 150,\n" +
                "      \"minCost\": 1500\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Daily Sushi\",\n" +
                "    \"status\": \"closed\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 9.0,\n" +
                "      \"newest\": 221.0,\n" +
                "      \"ratingAverage\": 4.0,\n" +
                "      \"distance\": 1911,\n" +
                "      \"popularity\": 6.0,\n" +
                "      \"averageProductPrice\": 1327,\n" +
                "      \"deliveryCosts\": 200,\n" +
                "      \"minCost\": 1000\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Pamukkale\",\n" +
                "    \"status\": \"closed\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 10.0,\n" +
                "      \"newest\": 201.0,\n" +
                "      \"ratingAverage\": 4.0,\n" +
                "      \"distance\": 2353,\n" +
                "      \"popularity\": 25.0,\n" +
                "      \"averageProductPrice\": 968,\n" +
                "      \"deliveryCosts\": 0,\n" +
                "      \"minCost\": 2000\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Indian Kitchen\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 11.0,\n" +
                "      \"newest\": 272.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 2308,\n" +
                "      \"popularity\": 5.0,\n" +
                "      \"averageProductPrice\": 1189,\n" +
                "      \"deliveryCosts\": 150,\n" +
                "      \"minCost\": 1300\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"CIRO 1939\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 12.0,\n" +
                "      \"newest\": 231.0,\n" +
                "      \"ratingAverage\": 4.5,\n" +
                "      \"distance\": 3957,\n" +
                "      \"popularity\": 79.0,\n" +
                "      \"averageProductPrice\": 1762,\n" +
                "      \"deliveryCosts\": 99,\n" +
                "      \"minCost\": 1300\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Zenzai Sushi\",\n" +
                "    \"status\": \"closed\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 13.0,\n" +
                "      \"newest\": 155.0,\n" +
                "      \"ratingAverage\": 4.0,\n" +
                "      \"distance\": 2911,\n" +
                "      \"popularity\": 36.0,\n" +
                "      \"averageProductPrice\": 1579,\n" +
                "      \"deliveryCosts\": 0,\n" +
                "      \"minCost\": 2000\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Fes Patisserie\",\n" +
                "    \"status\": \"order ahead\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 14.0,\n" +
                "      \"newest\": 77.0,\n" +
                "      \"ratingAverage\": 4.0,\n" +
                "      \"distance\": 2302,\n" +
                "      \"popularity\": 3.0,\n" +
                "      \"averageProductPrice\": 1214,\n" +
                "      \"deliveryCosts\": 150,\n" +
                "      \"minCost\": 1250\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Yvonne's Vispaleis\",\n" +
                "    \"status\": \"order ahead\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 15.0,\n" +
                "      \"newest\": 150.0,\n" +
                "      \"ratingAverage\": 5.0,\n" +
                "      \"distance\": 2909,\n" +
                "      \"popularity\": 3.0,\n" +
                "      \"averageProductPrice\": 2557,\n" +
                "      \"deliveryCosts\": 150,\n" +
                "      \"minCost\": 1750\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"De Amsterdamsche Tram\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 304.0,\n" +
                "      \"newest\": 131.0,\n" +
                "      \"ratingAverage\": 0.0,\n" +
                "      \"distance\": 2792,\n" +
                "      \"popularity\": 0.0,\n" +
                "      \"averageProductPrice\": 892,\n" +
                "      \"deliveryCosts\": 0,\n" +
                "      \"minCost\": 0\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Lale Restaurant & Snackbar\",\n" +
                "    \"status\": \"order ahead\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 305.0,\n" +
                "      \"newest\": 73.0,\n" +
                "      \"ratingAverage\": 0.0,\n" +
                "      \"distance\": 2880,\n" +
                "      \"popularity\": 0.0,\n" +
                "      \"averageProductPrice\": 838,\n" +
                "      \"deliveryCosts\": 0,\n" +
                "      \"minCost\": 0\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"name\": \"Lunchpakketdienst\",\n" +
                "    \"status\": \"open\",\n" +
                "    \"sortingValues\": {\n" +
                "      \"bestMatch\": 306.0,\n" +
                "      \"newest\": 259.0,\n" +
                "      \"ratingAverage\": 3.5,\n" +
                "      \"distance\": 14201,\n" +
                "      \"popularity\": 0.0,\n" +
                "      \"averageProductPrice\": 4465,\n" +
                "      \"deliveryCosts\": 500,\n" +
                "      \"minCost\": 5000\n" +
                "    }\n" +
                "  }]\n" +
                "}"
        val response = moshi.adapter(RestaurantListResponse::class.java).fromJson(jsonString)
        return Flowable.just(response)
    }

    override fun saveRestaurant(entity: RestaurantEntity): Completable {
        return dataBase
            .restaurantDao()
            .saveRestaurant(entity)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun getRestaurantByName(name: String?): Single<RestaurantEntity> {
        return dataBase
            .restaurantDao()
            .getRestaurantByName(name)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun deleteRestaurant(entity: RestaurantEntity): Completable {
        return dataBase
            .restaurantDao()
            .deleteRestaurant(entity)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
    }

    override fun getRestaurantsLocally(): Flowable<List<RestaurantEntity>> {
        return dataBase
            .restaurantDao()
            .getRestaurants()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}