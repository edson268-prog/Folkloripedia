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
                MythDto(
                    "009",
                    "El tío de la mina",
                    "Oruro - Potosí",
                    "https://firebasestorage.googleapis.com/v0/b/folkloripedia.appspot.com/o/Myths%2Ftio_de_la_mina_webp.webp?alt=media&token=e8cb20ce-8352-4f13-bdc4-898c7ad3430e",
                    "La explotación minera en Bolivia tiene una larga historia. Iniciada en tiempos de la colonia española, este tipo de actividad ha supuesto muchos beneficios tanto para la antigua metrópolis como para la actual república andina.||\n" +
                            "Sin embargo, también es cierto que ha supuesto miles de muertes, incluso hay quienes hablan de millones. Bajar a la mina es una actividad peligrosa, y quienes lo hacen rinden tributo a un poder sobrenatural colocando en su honor figurines rodeados por cervezas, cigarros e, incluso, animales sacrificados a quien tutela la vida de los mineros cuando se encuentran en sus dominios.||En la región del Potosí todos los mineros conocen la leyenda de “El Tío”, aquel que dicen que los cuida cuando se encuentran bajo tierra. El mundo subterráneo es el dominio de El Tío, que no es más que un eufemismo para referirse al Diablo. Quienes creen en esta leyenda consideran que el dominio de Dios no alcanza bajo tierra y, por eso, los mineros se entregan a la tutela del Diablo cuando están ahí abajo.||Adorando a El Tío, los hombres y, tristemente también, los niños que a día de hoy son explotados en las minas de Bolivia tienen la esperanza de recibir protección. Mientras El Tío esté contento, podrán regresar a casa.",
                    "Mining exploitation in Bolivia has a long history. Started in times of the Spanish colony, this type of activity has brought many benefits to both the ancient metropolis and the current Andean republic.||\n" +
                            "However, it is also true that it has led to thousands of deaths, there are even those who speak of millions. Going down the mine is a dangerous activity, and those who do it pay tribute to a supernatural power by placing figurines in their honor surrounded by beers, cigarettes and even animals sacrificed to the one who protects the lives of the miners when they are in their domain. ||In the Potosí region all miners know the legend of “El Tío”, the one who they say takes care of them when they are underground. The underworld is the domain of El Tío, which is nothing more than a euphemism for the Devil. Those who believe in this legend consider that God's dominion does not reach underground and, therefore, the miners surrender to the Devil's tutelage when they are down there.||Worshiping El Tío, the men and, sadly, also the children Those who are currently exploited in the mines of Bolivia have the hope of receiving protection. As long as El Tío is happy, they can return home.",
                    "MY",
                ),
                MythDto(
                    "010",
                    "Lluvia y sequía",
                    "-",
                    "https://firebasestorage.googleapis.com/v0/b/folkloripedia.appspot.com/o/Myths%2Flluvia_webp.webp?alt=media&token=a3e88bfa-1548-4c41-9ac2-370497869dd6",
                    "Una de las leyendas más antiguas de los pueblos indígenas de Bolivia es la que cuenta que Pachamama, la Madre Tierra, y el dios Huayra Tata, el dios del viento, eran pareja. Huayra Tata vivía en el tope de los cerros y los abismos y, cada cierto tiempo, bajaba y vaciaba el lago Titicacapara fecundar a Pachamama, dejando luego caer el agua haciendo llover.||Este dios a veces se quedaba dormido en el lago, lo cual hacía que las aguas se turbaran. A pesar de ello, siempre regresaba a las cumbres, que era su morada de residencia habitual y, cuando lo deseaba, volvía a visitar el lago para poder volver a intimar con su pareja. Esta es la historia que cuentan los grupos de Bolivia para explicar el por qué de las precipitaciones, la riqueza ecológicade su tierra y los ciclos del agua.",
                    "One of the oldest legends of the indigenous people of Bolivia is the one that tells that Pachamama, Mother Earth, and the god Huayra Tata, the god of the wind, were a couple. Huayra Tata lived at the top of the hills and abysses and, from time to time, he would come down and empty Lake Titicaca to fertilize Pachamama, then letting the water fall making it rain.||This god sometimes fell asleep in the lake, which which caused the waters to become troubled. Despite this, he always returned to the peaks, which was his habitual residence and, when he wished, he returned to visit the lake to be able to become intimate with his partner again. This is the story that Bolivian groups tell to explain the reason for rainfall, the ecological richness of their land and the water cycles.",
                    "MY",
                ),
                MythDto(
                    "011",
                    "Los fantasmas del hospital de clínicas",
                    "La Paz",
                    "https://firebasestorage.googleapis.com/v0/b/folkloripedia.appspot.com/o/Myths%2Ffantasmas-hosp.webp?alt=media&token=c9216185-866f-4ba6-8431-9b710615acc9",
                    "Dicen que el Hospital General de La Paz es un lugar frecuentado por fantasmas, espectros que abandonan su descanso de ultratumba para darse un volteo por las salas de la casa de enfermos y malheridos. Son muchas las historias que se cuentan de este lugar que, aunque de día es amigable, de noche parece que lo envuelve un halo de misterio y tenebrosidad, pero es especialmente interesante la que le ocurrió a una enfermera de nombre Wilma Huañapaco, encargada de la sala de Terapia Intensiva en el primer piso del edificio, quien nunca olvidará lo que sucedió un 4 de agosto.||Justo cinco minutos antes de que sonaran las dos de la madrugada, Huañapaco transcribía, como cada noche el reporte del estado de los pacientes. Una tarea realmente delicada, tanto que no consiente error alguno y requería que quien la realizaba estuviera despejada, despierta a pesar de lo tarde que era.||Pero, de repente, su cuerpo fue invadido por una pesadez repentina que la paralizó. No podía mover ni brazos ni piernas, ni siquiera sus párpados. Se había quedado como en estado vegetal, absolutamente inmóvil pero bien consciente en todo momento. Su desesperación al entrar en semejante estado la llevó a realizar un gran esfuerzo para poder voltearse. Al lograrlo consiguió ver la silueta de un hombre alto, contorneado por un aura de color verde oliva y ¡sin cabeza! que se desvaneció en instantes...||Cuando se lo contó a sus compañeras, algunas se mostraron incrédulas, aunque tampoco tanto. Ese hospital encierra algo, algo misterioso entre sus muros. De hecho, Wilma no es la única que ha visto apariciones en ese misterioso lugar, ni tampoco la primera en ver la silueta de un hombre decapitado.||Tanto algunos pacientes como parte de los médicos más veteranos en el lugar cuentan la historia de un hombre que cada noche se pasea por los jardines próximos al hospital del Tórax, rumbo a la morgue. Algunos lo han bautizado con el nombre del Jinete sin Cabeza, aunque no tiene relación alguna con el famoso relato del escritor estadounidense Washington Irving.",
                    "They say that the General Hospital of La Paz is a place frequented by ghosts, specters that abandon their afterlife rest to wander through the rooms of the house of the sick and badly injured. There are many stories that are told about this place that, although it is friendly during the day, at night it seems to be surrounded by a halo of mystery and darkness, but what happened to a nurse named Wilma Huañapaco, in charge of the care, is especially interesting. Intensive Care room on the first floor of the building, who will never forget what happened on August 4.||Just five minutes before two in the morning struck, Huañapaco was transcribing, like every night, the report on the patients' status . A truly delicate task, so much so that it allows no error and required that whoever performed it be alert, awake despite how late it was.||But, suddenly, her body was invaded by a sudden heaviness that paralyzed her. He couldn't move his arms or legs, not even his eyelids. He had remained as if in a vegetative state, absolutely motionless but well conscious at all times. Her desperation upon entering such a state led her to make a great effort to turn around. Upon achieving this he managed to see the silhouette of a tall man, outlined by an olive green aura and without a head! that vanished in moments...||When she told her colleagues, some were incredulous, although not that much. That hospital contains something, something mysterious within its walls. In fact, Wilma is not the only one who has seen apparitions in that mysterious place, nor is she the first to see the silhouette of a decapitated man.||Both some patients and some of the most senior doctors in the place tell the story of a man who every night walks through the gardens near the Thorax Hospital, heading to the morgue. Some have baptized him with the name of the Headless Horseman, although it has no relation to the famous story by the American writer Washington Irving.",
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