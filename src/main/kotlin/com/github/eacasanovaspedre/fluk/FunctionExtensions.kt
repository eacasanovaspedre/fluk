package com.github.eacasanovaspedre.fluk

operator fun <P1, P2, R> ((P1, P2) -> R).invoke(p1: P1): (P2) -> R {
    return { p2 -> this(p1, p2) }
}

operator fun <P1, P2, P3, R> ((P1, P2, P3) -> R).invoke(p1: P1): (P2, P3) -> R {
    return { p2, p3 -> this(p1, p2, p3) }
}

operator fun <P1, P2, P3, R> ((P1, P2, P3) -> R).invoke(p1: P1, p2: P2): (P3) -> R {
    return { p3 -> this(p1, p2, p3) }
}

operator fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).invoke(p1: P1): (P2, P3, P4) -> R {
    return { p2, p3, p4 -> this(p1, p2, p3, p4) }
}

operator fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).invoke(p1: P1, p2: P2): (P3, P4) -> R {
    return { p3, p4 -> this(p1, p2, p3, p4) }
}

operator fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).invoke(p1: P1, p2: P2, p3: P3): (P4) -> R {
    return { p4 -> this(p1, p2, p3, p4) }
}

operator fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).invoke(p1: P1): (P2, P3, P4, P5) -> R {
    return { p2, p3, p4, p5 -> this(p1, p2, p3, p4, p5) }
}

operator fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).invoke(p1: P1, p2: P2): (P3, P4, P5) -> R {
    return { p3, p4, p5 -> this(p1, p2, p3, p4, p5) }
}

operator fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).invoke(p1: P1, p2: P2, p3: P3): (P4, P5) -> R {
    return { p4, p5 -> this(p1, p2, p3, p4, p5) }
}

operator fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).invoke(p1: P1, p2: P2, p3: P3, p4: P4): (P5) -> R {
    return { p5 -> this(p1, p2, p3, p4, p5) }
}