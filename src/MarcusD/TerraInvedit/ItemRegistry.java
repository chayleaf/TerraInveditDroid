package MarcusD.TerraInvedit;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;

public class ItemRegistry
{
	public Map<Short, ItemEntry> basemap;
	
	public static ItemRegistry instance;
	
	public boolean useimg = false;
	
	public ItemRegistry(Context ctx)
	{
		basemap = new DefaultHashMap<Short, ItemRegistry.ItemEntry>(new ItemEntry((short)0, ctx.getString(R.string.javatrans_item_empty), null));
		add((short)0, "* Empty *");
        add((short)-1, "Gold Pickaxe");
        add((short)-2, "Gold Broadsword");
        add((short)-3, "Gold Shortsword");
        add((short)-4, "Gold Axe");
        add((short)-5, "Gold Hammer");
        add((short)-6, "Gold Bow");
        add((short)-7, "Silver Pickaxe");
        add((short)-8, "Silver Broadsword");
        add((short)-9, "Silver Shortsword");
        add((short)-10,"Silver Axe");
        add((short)-11, "Silver Hammer");
        add((short)-12, "Silver Bow");
        add((short)-13, "Copper Pickaxe");
        add((short)-14, "Copper Broadsword");
        add((short)-15, "Copper Shortsword");
        add((short)-16, "Copper Axe");
        add((short)-17, "Copper Hammer");
        add((short)-18, "Copper Bow");
        add((short)-19, "Blue Phasesaber");
        add((short)-20, "Red Phasesaber");
        add((short)-21, "Green Phasesaber");
        add((short)-22, "Purple Phasesaber");
        add((short)-23, "White Phasesaber");
        add((short)-24, "Yellow Phasesaber");
        add((short)-25, "Tin Pickaxe");
        add((short)-26, "Tin Broadsword");
        add((short)-27, "Tin Shortsword");
        add((short)-28, "Tin Axe");
        add((short)-29, "Tin Hammer");
        add((short)-30, "Tin Bow");
        add((short)-31, "Lead Pickaxe");
        add((short)-32, "Lead Broadsword");
        add((short)-33, "Lead Shortsword");
        add((short)-34, "Lead Axe");
        add((short)-35, "Lead Hammer");
        add((short)-36, "Lead Bow");
        add((short)-37, "Tungsten Pickaxe");
        add((short)-38, "Tungsten Broadsword");
        add((short)-39, "Tungsten Shortsword");
        add((short)-40, "Tungsten Axe");
        add((short)-41, "Tungsten Hammer");
        add((short)-42, "Tungsten Bow");
        add((short)-43, "Platinum Pickaxe");
        add((short)-44, "Platinum Broadsword");
        add((short)-45, "Platinum Shortsword");
        add((short)-46, "Platinum Axe");
        add((short)-47, "Platinum Hammer");
        add((short)-48, "Platinum Bow");
	}
	
	public void add(short s, ItemEntry ie)
	{
		basemap.put(new Short(s), ie);
	}
	
	public void add(short i, String s)
	{
		add(i, new ItemEntry(i, s, null));
	}
	
	public class ItemEntry
	{
		public ItemEntry(short id, String name, Bitmap i)
		{
			this.iid = id;
			this.iname = name;
			this.img = i;
		}
		public String iname;
		public short iid;
		public Bitmap img;
		
		@Override
		public String toString()
		{
			return iname;
		}
	}
	
	public enum Buff
	{
		_,
		Large,
		Massive,
		Dangerous,
		Savage,
		Sharp,
		Pointy,
		Tiny,
		Terrible,
		Small,
		Dull,
		Unhappy,
		Bulky,
		Shameful,
		Heavy,
		Light,
		Sighted,
		Rapid,
		Hasty2,
		Intimidating,
		Deadly2,
		Staunch,
		Awful,
		Lethargic,
		Awkward,
		Powerful,
		Mystic,
		Adept,
		Masterful,
		Inept,
		Ignorant,
		Deranged,
		Intense,
		Taboo,
		Celestial,
		Furious,
		Keen,
		Superior,
		Forceful,
		Broken,
		Damaged,
		Shoddy,
		Quick2,
		Deadly,
		Agile,
		Nimble,
		Murderous,
		Slow,
		Sluggish,
		Lazy,
		Annoying,
		Nasty,
		Manic,
		Hurtful,
		Strong,
		Unpleasant,
		Weak,
		Ruthless,
		Frenzying,
		Godly,
		Demonic,
		Zealous,
		Hard,
		Guarding,
		Armored,
		Warding,
		Arcane,
		Precise,
		Lucky,
		Jagged,
		Spiked,
		Angry,
		Menacing,
		Brisk,
		Fleeting,
		Hasty,
		Quick,
		Wild,
		Rash,
		Intrepid,
		Violent,
		Legendary,
		Unreal;

		public static Buff n(byte i)
        {
        	try
        	{
        		return Buff.values()[i];
        	}
        	catch(ArrayIndexOutOfBoundsException dummy)
        	{
        		return Buff._;
        	}
        }
	}
	
	public class DefaultHashMap<K,V> extends HashMap<K,V> {
		private static final long serialVersionUID = -1L;
		protected V defaultValue;
		  public DefaultHashMap(V defaultValue) {
		    this.defaultValue = defaultValue;
		  }
		  @Override
		  public V get(Object k) {
		    return containsKey(k) ? super.get(k) : defaultValue;
		  }
		}
}
