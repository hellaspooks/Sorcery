package com.root.sorcery.spell;

import com.root.sorcery.Config;
import net.minecraft.util.ActionResultType;

public class MagicMissileSpell extends Spell
{

    private int damage;

    public MagicMissileSpell()
    {
        super(Config.MAGIC_MISSILE_SPELL_COST.get());
        this.damage = Config.MAGIC_MISSILE_SPELL_DAMAGE.get();
    }

    @Override
    public ActionResultType castServer(SpellUseContext context) {
        //TODO add server side code
        return super.castServer(context);
    }
}
