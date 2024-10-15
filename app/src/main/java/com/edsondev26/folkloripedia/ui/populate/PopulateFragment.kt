package com.edsondev26.folkloripedia.ui.populate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.edsondev26.folkloripedia.databinding.FragmentPopulateBinding
import com.edsondev26.folkloripedia.domain.dto.DanceDto
import com.edsondev26.folkloripedia.domain.dto.EventDto
import com.edsondev26.folkloripedia.domain.dto.MythDto
import com.edsondev26.folkloripedia.ui.populate.adapter.PopulateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopulateFragment : Fragment() {
    private lateinit var binding: FragmentPopulateBinding
    private val populateViewModel by viewModels<PopulateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopulateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnRegisterEvents.setOnClickListener {
            val events = listOf(
                EventDto(
                    "001",
                    "Feria de la Alasita",
                    " Alasitas Fair",
                    "Conocida tambien como la fiesta de Ekeko para los bolivianos el Dios de la Abundancia, se construyen muñecos de maderas ataviados con la indumentaria tradicional, de cuyos brazos abiertos penden los deseos de los participantes: dinero, coches, ordenadores, etc. Estas figuritas se rocían con alcohol y pétalos de flores, mientras se les echa humo de tabaco y se rezan oraciones.",
                    "Also known as the Ekeko festival for the Bolivians, the God of Abundance, wooden dolls dressed in traditional clothing are built, from whose open arms hang the wishes of the participants: money, cars, computers, etc. These figurines are sprinkled with alcohol and flower petals, while tobacco smoke is blown on them and prayers are said.",
                    "https://firebasestorage.googleapis.com/v0/b/folkloripedia.appspot.com/o/Events%2Falasitas_webp.webp?alt=media&token=ba206f06-99a1-4cfc-8b3c-286d4a401350",
                    "JAN",
                    "24"
                ),
            )
            populateViewModel.fetchInsertEvents(events)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    populateViewModel.eventItem.collect { insertedEvents ->
                        Log.d("Populate", "Registered Events: $insertedEvents")
                    }
                }
            }
        }

        binding.btnRegisterDances.setOnClickListener {
            val dances = listOf(
                DanceDto(
                    "003",
                    "Saya",
                    "La danza y la música de la Saya son originarias del valle de los Yungas (La Paz) y representativas de la cultura afro andino.||El ritmo de la Saya esta dado por el sonido sordo de tambor (bombo) que resuena como la doble palpitación del corazón y se caracterizada por el tintineo de campanillas del caporal (capataz elegido por los colonizadores españoles para supervisar el trabajo de los esclavos) que dirige la danza agitando un látigo.||Los hombres tocan el tambor mientras que las mujeres cantan y bailan removiendo las caderas, los hombros y los brazos.||Los comportamientos de los bailarines son relativamente simples: las mujeres llevan un largo vestido (pollera), una blusa de color, un mantón (manta), y un sombrero borsalino, y los hombres camisa, pantalones y sandalias.",
                    "The dance and music of the Saya are originally from the Yungas Valley (La Paz) and representative of the Afro-Andean culture.||The rhythm of the Saya is given by the dull sound of the drum (bombo) that resonates like the double palpitation of the heart and is characterized by the tinkling of bells of the caporal (foreman chosen by the Spanish colonizers to supervise the work of the slaves) who leads the dance by waving a whip.||The men play the drum while the women sing and dance moving the hips, shoulders and arms.||The dancers' behaviors are relatively simple: the women wear a long dress (pollera), a colored blouse, a shawl (manta), and a borsalino hat, and the men shirt, pants and sandals.",
                    "https://firebasestorage.googleapis.com/v0/b/folkloripedia.appspot.com/o/Dances%2Fsaya_webp.webp?alt=media&token=5137e8c4-23dc-4ccd-825b-3f6011b37f80",
                    "Bombo, Huancara, Caja, Tambores de saya",
                    "Bass drum, Huancara, Caja, Saya drums",
                    "La Paz",
                    "DA",
                    "https://firebasestorage.googleapis.com/v0/b/folkloripedia.appspot.com/o/Dances%2Fsaya_traje_webp.webp?alt=media&token=101a2f9f-3e34-4509-b53b-e450992c2275",
                    "1988"
                ),
            )

            populateViewModel.fetchInsertDances(dances)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    populateViewModel.danceItem.collect { insertedDances ->
                        Log.d("Populate", "Registered Dances: $insertedDances")
                    }
                }
            }
        }

        binding.btnRegisterMyths.setOnClickListener {
            val myths = listOf(
                MythDto(
                    "008",
                    "Toborochi",
                    "Santa Cruz",
                    "https://firebasestorage.googleapis.com/v0/b/folkloripedia.appspot.com/o/Myths%2Ftoborochi_webp.webp?alt=media&token=098b5ca6-4ddb-4118-ac99-b033b17aee42",
                    "El toborochi es un arbol nativo de Santa Cruz de la Sierra que posee una forma bastante peculiar y florece en la víspera del otoño con bellas flores de color rosado.||Según la leyenda de origen guaraní, Araverá, la hija del cacique, se casó con el dios Colibrí. Muy felices y enamorados, esperaban el nacimiento de su primer hijo que, según las predicciones, crecería para convertirse en un famoso chamán.||Sin embargo, los Añas, deidades malignas, querían acabar con ese niño que podría amenazar su fuerza. Asustada, Araverá huyó en una silla voladora que le había regalado su esposo. Para su mala fortuna, los Añas resultaron ser implacables y la buscaron por todos lados.||Así, volvió al bosque cercano a su aldea y se ocultó dentro de un toborochi que la acogió amablemente y poco a poco fue tomando la forma de su panza.||Finalmente, Araverá pudo dar a luz a un chico fuerte que podría cumplir su destino. No obstante, ella decidió quedarse dentro del árbol y se convirtió en parte de su esencia. De todos modos, cada año sale en forma de flor para encontrarse con su amado colibrí que se alimenta de ella con su néctar y da paso a la nueva vida.",
                    "The toborochi is a tree native to Santa Cruz de la Sierra that has a quite peculiar shape and blooms on the eve of autumn with beautiful pink flowers.||According to the legend of Guaraní origin, Araverá, the chief's daughter, married with the god Hummingbird. Very happy and in love, they awaited the birth of their first son who, according to predictions, would grow up to become a famous shaman.||However, the Añas, evil deities, wanted to put an end to this child who could threaten their strength. Frightened, Araverá fled in a flying chair that her husband had given her. Unfortunately for her, the Añas turned out to be relentless and looked for her everywhere.||Thus, she returned to the forest near her village and hid inside a toborochi that kindly welcomed her and little by little it took the shape of her belly. .||Finally, Araverá was able to give birth to a strong boy who could fulfill his destiny. However, she decided to stay inside the tree and became part of its essence. Anyway, every year she comes out in the form of a flower to meet her beloved hummingbird who feeds her with her nectar and gives way to new life.",
                    "MY",
                ),
            )

            populateViewModel.fetchInsertMyths(myths)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    populateViewModel.mythItem.collect { insertedMyths ->
                        Log.d("Populate", "Registered Myths: $insertedMyths")
                    }
                }
            }
        }
    }
}