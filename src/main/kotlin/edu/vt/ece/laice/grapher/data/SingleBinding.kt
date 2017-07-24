package edu.vt.ece.laice.grapher.data

/**
 * Created by cameronearle on 6/23/17.
 */
class SingleBinding(override val table: Tables,
                         override val internalName: String,
                         override val prettyName: String,
                         override val units: String,
                    val group: BindingGroup? = null): Binding()